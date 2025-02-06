package priv.mansour.school.logger;

import lombok.extern.slf4j.Slf4j;
import priv.mansour.school.utils.DateUtil;

@Slf4j
public class GlobalLogger {

	private GlobalLogger() {
	}

	public static void infoCreate(String entity, Object data) {
		log.info("[{}] - [CONTROLLER] Received POST request to create {} - Data: {}", DateUtil.now(), entity, data);
	}

	public static void infoRead(String entity, String id) {
		log.info("[{}] - [CONTROLLER] Received GET request for {} - ID: {}", DateUtil.now(), entity, id);
	}

	public static void infoUpdate(String entity, String id, Object updatedData) {
		log.info("[{}] - [CONTROLLER] Received PUT request to update {} - ID: {} - Updated Data: {}", DateUtil.now(),
				entity, id, updatedData);
	}

	public static void infoDelete(String entity, String id) {
		log.info("[{}] - [CONTROLLER] Received DELETE request to remove {} - ID: {}", DateUtil.now(), entity, id);
	}

	public static void warnNotFound(String entity, String id) {
		log.warn("[{}] - [CONTROLLER] {} not found - ID: {}", DateUtil.now(), entity, id);
	}

	public static void error(String entity, String action, Exception ex) {
		log.error("[{}] - [CONTROLLER] Error while processing {} - Action: {} - Message: {}", DateUtil.now(), entity,
				action, ex.getMessage(), ex);
	}
}
