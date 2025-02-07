package priv.mansour.school.logger;

import lombok.extern.slf4j.Slf4j;
import priv.mansour.school.utils.DateUtil;

@Slf4j
public class GlobalLogger {

	private GlobalLogger() {
	}

	public static void infoReceived(String method, String entity, String details) {
		log.info("[{}] Received {} request for {} - {}", DateUtil.now(), method, entity, details);
	}

	public static void infoCreate(String entity, Object data) {
		log.info("[{}] Received POST request to create {} - Data: {}", DateUtil.now(), entity, data);
	}

	public static void infoRead(String entity, String id) {
		log.info("[{}] Received GET request for {} - ID: {}", DateUtil.now(), entity, id);
	}

	public static void infoReadAll(String entity) {
		log.info("[{}] Received GET request to retrieve all {}", DateUtil.now(), entity);
	}

	public static void infoUpdate(String entity, String id, Object updatedData) {
		log.info("[{}] Received PUT request to update {} - ID: {} - Updated Data: {}", DateUtil.now(), entity, id,
				updatedData);
	}

	public static void infoDelete(String entity, String id) {
		log.info("[{}] Received DELETE request to remove {} - ID: {}", DateUtil.now(), entity, id);
	}

	public static void infoAction(String action, String entity, Object details) {
		log.info("[{}] {} - {} - {}", DateUtil.now(), action, entity, details);
	}

	public static void infoSuccess(String action, String entity, Object details) {
		log.info("[{}] Successfully {} - {} - {}", DateUtil.now(), action, entity, details);
	}

	public static void warnNotFound(String entity, String id) {
		log.warn("[{}] {} not found - ID: {}", DateUtil.now(), entity, id);
	}

	public static void warnDuplicate(String entity, String id) {
		log.warn("[{}] {} Already Exists- ID: {}", DateUtil.now(), entity, id);
	}

	public static void error(String entity, String action, Exception ex) {
		log.error("[{}] Error while processing {} - Action: {} - Message: {}", DateUtil.now(), entity, action,
				ex.getMessage(), ex);
	}

	public static void warnEmptyField(String fieldName) {
		log.warn("[{}] Validation Warning: Le champ '{}' ne doit pas être vide.", DateUtil.now(), fieldName);
	}
}
