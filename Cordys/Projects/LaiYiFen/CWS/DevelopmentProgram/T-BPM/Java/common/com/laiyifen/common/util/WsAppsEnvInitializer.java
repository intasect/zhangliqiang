package com.laiyifen.common.util;

import java.io.UnsupportedEncodingException;

import com.cordys.cpc.bsf.busobject.BSF;
import com.cordys.cpc.bsf.busobject.BsfContext;
import com.cordys.cpc.bsf.busobject.Config;
import com.eibus.xml.nom.Node;
import com.eibus.xml.nom.XMLException;

public class WsAppsEnvInitializer {
	private static String bdsConfigString = "<configurations>"
			+ "<configuration implementation=\"com.cordys.cpc.bsf.connector.BsfConnector\">"
			+ "<ruleEnabled>false</ruleEnabled>"
			+ "<auditEnabled>off</auditEnabled>"
			+ "<initializeDB>true</initializeDB>"
			+ "<initializeEIS>false</initializeEIS>"
			+ "<customInitializer/>"
			+ "<applicationInitializer/>"
			+ "<autoCleanup>false</autoCleanup>"
			+ "<bsfuser>cn=SYSTEM,cn=organizational users,o=laiyifen,cn=cordys,cn=defaultInst,o=laiyifen</bsfuser>"
			+ "<component name=\"WS-AppServerConnectionPool\">"
			+ "<xmlEncoding>false</xmlEncoding>"
			+ "<precedence/>"
			+ "<cursorCacheSize>50</cursorCacheSize>"
			+ "<cursorCacheRefreshInterval>30</cursorCacheRefreshInterval>"
			+ "<queryCacheSize>50</queryCacheSize>"
			+ "<queryCacheRefreshInterval>3600</queryCacheRefreshInterval>"
			+ "<maximumWriteConnections>4</maximumWriteConnections>"
			+ "<maximumReadConnections>16</maximumReadConnections>"
			+ "<minimumReadConnections>1</minimumReadConnections>"
			+ "<minimumWriteConnections>1</minimumWriteConnections>"
			+ "<connectionPoolRefreshInterval>3600</connectionPoolRefreshInterval>"
			+ "<multithreaded>true</multithreaded>"
			+ "<sharedPool>false</sharedPool>"
			+ "<xsiNilForNullData>true</xsiNilForNullData>" + "<datasource>"
			+ "<type>Relational</type>" + "<name>tbpmDB#laiyifen</name>"
			+ "</datasource>" + "</component>" + "</configuration>"
			+ "</configurations>";
	static {
		initializeConfig();
	}

	public static void initializeConfig() {
		int wsAppConfigNode = 0;
		try {
			wsAppConfigNode = CommonUtil.getDefaultDocument().parseString(
					bdsConfigString);
			Config wsAppConfig = new Config();
			wsAppConfig.setConfig(wsAppConfigNode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		} catch (XMLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			Node.delete(wsAppConfigNode);
			wsAppConfigNode = 0;
		}
	}

	public static BsfContext initEmbeddedBsfContext() throws Exception {
		try {
			return BSF.initBsfContext();
			// return context;
		} catch (Exception e) {
			throw new RuntimeException(
					"Embedded WsApps Manager initialization for context failed :"
							+ AssignmentUtils.getDetailError(e));
		}
	}

	public static void releaseBsfContext(BsfContext context) {
		try {
			BSF.unregisterContext(context);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String getOrganizationDN() {
		return BSF.getOrganization();
	}

	public String getUserDN() {
		return BSF.getUser();
	}
}