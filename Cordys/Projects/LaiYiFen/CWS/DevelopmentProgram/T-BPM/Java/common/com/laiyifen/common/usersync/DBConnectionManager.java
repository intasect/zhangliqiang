package com.laiyifen.common.usersync;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class is a Singleton that provides access to one or many connection
 * pools defined in a Property file. A client gets access to the single instance
 * through the static getInstance() method and can then check-out and check-in
 * connections from a pool. When the client shuts down it should call the
 * release() method to close all open connections and do other clean up.
 */
public class DBConnectionManager
{
	static private DBConnectionManager instance; // The single instance

	static private int clients;

	private Vector drivers = new Vector();

	//private PrintWriter					log;

	private Hashtable pools = new Hashtable();

	private String driverName;

	private String url;

	//private String 						databaseName;
	private String userName;

	private String password;

	private int readConnections;

	/**
	 * Returns the single instance, creating one if it's the first time this
	 * method is called.
	 *
	 * @return DBConnectionManager The single instance.
	 */
	static synchronized public DBConnectionManager getInstance(String driverName, String url,
			String userName, String password, int readConnections)
	{
		if (instance == null)
		{
			instance = new DBConnectionManager(driverName, url, userName, password,
					readConnections);
		}
		clients++;
		LogHandler.info("Get connection pool instance. ");
		return instance;
	}

	/**
	 * A private constructor since this is a Singleton
	 */
	private DBConnectionManager(String driverName, String url, String userName, String password,
			int readConnections)
	{
		this.driverName = driverName;
		this.url = url;
		//this.databaseName = databaseName;
		this.userName = userName;
		this.password = password;
		this.readConnections = readConnections;
		LogHandler.info("Get connection pool constructor. ");
		init();
	}

	/**
	 * Returns a connection to the named pool.
	 *
	 * @param name
	 *            The pool name as defined in the properties file
	 * @param con
	 *            The Connection
	 */
	public void freeConnection(String name, Connection con)
	{
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null)
		{
			pool.freeConnection(con);
		}
	}

	/**
	 * Returns an open connection. If no one is available, and the max number of
	 * connections has not been reached, a new connection is created.
	 *
	 * @param name
	 *            The pool name as defined in the properties file
	 * @return Connection The connection or null
	 */
	public Connection getConnection(String name)
	{
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null)
		{
			return pool.getConnection();
		}
		return null;
	}

	/**
	 * Returns an open connection. If no one is available, and the max number of
	 * connections has not been reached, a new connection is created. If the max
	 * number has been reached, waits until one is available or the specified
	 * time has elapsed.
	 *
	 * @param name
	 *            The pool name as defined in the properties file
	 * @param time
	 *            The number of milliseconds to wait
	 * @return Connection The connection or null
	 */
	public Connection getConnection(String name, long time)
	{
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null)
		{
			return pool.getConnection(time);
		}
		return null;
	}

	/**
	 * Closes all open connections and deregisters all drivers.
	 */
	public synchronized void release()
	{
		// Wait until called by the last client
		if (--clients != 0)
		{
			return;
		}

		Enumeration allPools = pools.elements();
		while (allPools.hasMoreElements())
		{
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			pool.release();
		}
		Enumeration allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements())
		{
			Driver driver = (Driver) allDrivers.nextElement();
			try
			{
				DriverManager.deregisterDriver(driver);
				log("Deregistered JDBC driver " + driver.getClass().getName());
			} catch (SQLException e)
			{
				log(e, "Can't deregister JDBC driver: " + driver.getClass().getName());
			}
		}
	}

	/**
	 * Creates instances of DBConnectionPool based on the properties. A
	 * DBConnectionPool can be defined with the following properties:
	 *
	 * <PRE>
	 *
	 * &lt;poolname&gt;.url The JDBC URL for the database &lt;poolname&gt;.user
	 * A database user (optional) &lt;poolname&gt;.password A database user
	 * password (if user specified) &lt;poolname&gt;.maxconn The maximal number
	 * of connections (optional)
	 *
	 * </PRE>
	 *
	 * @param props
	 *            The connection pool properties
	 */
	private void createPools()
	{
		if (url == null)
		{
			log("No URL specified");
			return;
		}
		String user = userName;
		String password = this.password;
		int maxconn = readConnections;
		DBConnectionPool pool = new DBConnectionPool("ConnectionPool", url, user, password,
				maxconn);
		pools.put("ConnectionPool", pool);
		log("Initialized pool " + "\"ConnectionPool\", maxconn: " + maxconn);
	}

	/**
	 * Loads properties and initializes the instance with its values.
	 */
	private void init()
	{
		//log = new PrintWriter(System.out);
		loadDrivers();
		createPools();
	}

	/**
	 * Loads and registers all JDBC drivers. This is done by the
	 * DBConnectionManager, as opposed to the DBConnectionPool, since many pools
	 * may share the same driver.
	 *
	 * @param props
	 *            The connection pool properties
	 */
	private void loadDrivers()
	{
		try
		{
			Driver driver = (Driver) Class.forName(driverName).newInstance();
			DriverManager.registerDriver(driver);
			drivers.addElement(driver);
			log("Registered JDBC driver " + driverName);
		} catch (Exception e)
		{
			log("Can't register JDBC driver: " + driverName + ", Exception: " + e);
		}
	}

	/**
	 * Writes a message to the log file.
	 */
	private void log(String msg)
	{
	    LogHandler.info(msg);
	}

	/**
	 * Writes a message with an Exception to the log file.
	 */
	private void log(Throwable e, String msg)
	{
		LogHandler.error(msg,e);
	}

	/**
	 * This inner class represents a connection pool. It creates new connections
	 * on demand, up to a max number if specified. It also makes sure a
	 * connection is still open before it is returned to a client.
	 */
	class DBConnectionPool
	{
		private int checkedOut;

		private Vector freeConnections = new Vector();

		private int maxConn;

		private String name;

		private String password;

		private String URL;

		private String user;

		/**
		 * Creates new connection pool.
		 *
		 * @param name
		 *            The pool name
		 * @param URL
		 *            The JDBC URL for the database
		 * @param user
		 *            The database user, or null
		 * @param password
		 *            The database user password, or null
		 * @param maxConn
		 *            The maximal number of connections, or 0 for no limit
		 */
		public DBConnectionPool(String name, String URL, String user, String password,
				int maxConn)
		{
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/**
		 * Checks in a connection to the pool. Notify other Threads that may be
		 * waiting for a connection.
		 *
		 * @param con
		 *            The connection to check in
		 */
		public synchronized void freeConnection(Connection con)
		{
			// Put the connection at the end of the Vector
			freeConnections.addElement(con);
			checkedOut--;
			LogHandler.info("Free Connection.");
			LogHandler.info("free connections: " + freeConnections.size()
					+ ", checked out connections: " + checkedOut);
			notifyAll();
		}

		/**
		 * Checks out a connection from the pool. If no free connection is
		 * available, a new connection is created unless the max number of
		 * connections has been reached. If a free connection has been closed by
		 * the database, it's removed from the pool and this method is called
		 * again recursively.
		 */
		public synchronized Connection getConnection()
		{
			Connection con = null;
			if (freeConnections.size() > 0)
			{
				// Pick the first Connection in the Vector
				// to get round-robin usage
				con = (Connection) freeConnections.firstElement();
				freeConnections.removeElementAt(0);
				try
				{
					if (con.isClosed())
					{
						log("Removed bad connection from " + name);
						// Try again recursively
						con = getConnection();
					}
				} catch (SQLException e)
				{
					log("Removed bad connection from " + name);
					// Try again recursively
					con = getConnection();
				}
			} else if (maxConn == 0 || checkedOut < maxConn)
			{
				con = newConnection();
			}
			if (con != null)
			{
				checkedOut++;
			}
			LogHandler.info("free connections: " + freeConnections.size()
					+ ", checked out connections: " + checkedOut);
			return con;
		}

		/**
		 * Checks out a connection from the pool. If no free connection is
		 * available, a new connection is created unless the max number of
		 * connections has been reached. If a free connection has been closed by
		 * the database, it's removed from the pool and this method is called
		 * again recursively.
		 * <P>
		 * If no connection is available and the max number has been reached,
		 * this method waits the specified time for one to be checked in.
		 *
		 * @param timeout
		 *            The timeout value in milliseconds
		 */
		public synchronized Connection getConnection(long timeout)
		{
			long startTime = new Date().getTime();
			Connection con;
			while ((con = getConnection()) == null)
			{
				try
				{
					wait(timeout);
				} catch (InterruptedException e)
				{
				}
				if ((new Date().getTime() - startTime) >= timeout)
				{
					// Timeout has expired
					return null;
				}
			}
			return con;
		}

		/**
		 * Closes all available connections.
		 */
		public synchronized void release()
		{
			Enumeration allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements())
			{
				Connection con = (Connection) allConnections.nextElement();
				try
				{
					con.close();
					log("Closed connection for pool " + name);
				} catch (SQLException e)
				{
					log(e, "Can't close connection for pool " + name);
				}
			}
			freeConnections.removeAllElements();
		}

		/**
		 * Creates a new connection, using a userid and password if specified.
		 */
		private Connection newConnection()
		{
			Connection con = null;
			try
			{
				if (user == null)
				{
					con = DriverManager.getConnection(URL);
				} else
				{
					con = DriverManager.getConnection(URL, user, password);
				}
				log("Created a new connection in pool " + name);
			} catch (SQLException e)
			{
				log(e, "Can't create a new connection for " + URL);
				return null;
			}
			return con;
		}
	}
}