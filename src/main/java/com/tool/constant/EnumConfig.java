package com.tool.constant;

/**
 * 是否删除
 * @author wyj
 */
public interface EnumConfig {
	
	enum DB_NAME {
		BASE("base", "基本数据库"),
		SOURCE("source", "数据源"),
		GOLD("gold", "目标数据源"),
		PANICBUY("panicbuy", "一元购数据源");
		
		private String code;
		private String name;

		DB_NAME(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public static DB_NAME getEnum(String code) throws RuntimeException {
			DB_NAME[] pesEnum = DB_NAME.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (code.compareTo(pesEnum[i].getCode()) == 0) {
					return pesEnum[i];
				}
			}
			return null;
		}
		
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}

	enum S_CODE {
		OK(0, "成功！"),
		FAIL(1, "请求失败！"),
		DB_ERROR(2001, "操作数据库失败！"),
		PARAM_ERROR(100, "参数错误！"),
		TIME_OUT(11, "超时！"),
		LIMIT(12, "受限！"),
		TRANSFORMATION(13, "转换异常！"),
		ALREADY(14, "已经存在！"),
	    NOT_LOGIN(101,"未登录"),
	    TOKEN_TIMEOUT(104,"登录超时"),
	    USERNAME_OR_PWD_ERROR(105,"账户密码不正确！"),
	    NO_PERMISSIONS(107,"无权限！"),
	    NO_RECORD(108,"无记录！"),
	    VALIDATE_CODE_ERROR(109,"验证码不正确！"),
	    NO_PAGE(110, "未找到该页面！");
		
		private int code;
		private String name;

		S_CODE(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public static S_CODE getEnum(int code) throws RuntimeException {
			S_CODE[] pesEnum = S_CODE.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getCode() == code) {
					return pesEnum[i];
				}
			}
			return null;
		}
		
		public int getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	enum STATUS {
		OK(0, "ok"),
		CLOSE(1, "close");
		
		private int code;
		private String name;

		STATUS(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public static STATUS getEnum(int code) throws RuntimeException {
			STATUS[] pesEnum = STATUS.values();
				for (int i = 0; i < pesEnum.length; i++) {
					if (pesEnum[i].getCode() == code) {
						return pesEnum[i];
					}
				}
			return null;
		}
		
		public int getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	enum LEVEL {
		ZERO(0),
		ONE(1),
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5);
		
		private int code;

		LEVEL(int code) {
			this.code = code;
		}

		public static LEVEL getEnum(int code) throws RuntimeException {
			LEVEL[] pesEnum = LEVEL.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getCode() == code) {
					return pesEnum[i];
				}
			}
			return null;
		}
		
		public int getCode() {
			return code;
		}
	}
	
	/**
	 * 隐患治理-状态
	 */
	enum COMPANY_HIDDEN_STATUS {
		WCL(0, "未处理"),
		PCZ(1, "排查中"),
		YPC(2, "已排查"),
		ZGZ(3, "整改中"),
		YZG(4, "已整改");
		
		private int code;
		private String name;

		COMPANY_HIDDEN_STATUS(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public static COMPANY_HIDDEN_STATUS getEnum(int code) throws RuntimeException {
			COMPANY_HIDDEN_STATUS[] pesEnum = COMPANY_HIDDEN_STATUS.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getCode() == code) {
					return pesEnum[i];
				}
			}
			return null;
		}
		
		public int getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 隐患治理-检查类别
	 */
	enum COMPANY_HIDDEN_CHECK_TYPE {
		WCL(0, "自查"),
		PCZ(1, "抽查");
		
		private int code;
		private String name;

		COMPANY_HIDDEN_CHECK_TYPE(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public static COMPANY_HIDDEN_CHECK_TYPE getEnum(int code) throws RuntimeException {
			COMPANY_HIDDEN_CHECK_TYPE[] pesEnum = COMPANY_HIDDEN_CHECK_TYPE.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getCode() == code) {
					return pesEnum[i];
				}
			}
			return null;
		}
		
		public int getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 隐患治理-隐患类型
	 */
	enum COMPANY_HIDDEN_HID_TYPE {
		WD(0, "温度"),
		DL(1, "电流"),
		SYDL(2, "剩余电流"),
		SXBPH(3, "三相不平衡"),
		PLD(4, "偏离度");
		
		private int code;
		private String name;

		COMPANY_HIDDEN_HID_TYPE(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public static COMPANY_HIDDEN_HID_TYPE getEnum(int code) throws RuntimeException {
			COMPANY_HIDDEN_HID_TYPE[] pesEnum = COMPANY_HIDDEN_HID_TYPE.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getCode() == code) {
					return pesEnum[i];
				}
			}
			return null;
		}
		
		public int getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	enum JmsType{
		LOG("log");
		

		private String name;
		JmsType(String name) {
			this.name = name;
		}
		
		public static JmsType getEnum(String name) throws RuntimeException {
			JmsType[] pesEnum = JmsType.values();
			for (int i = 0; i < pesEnum.length; i++) {
				if (pesEnum[i].getName().equals(name)) {
					return pesEnum[i];
				}
			}
			return null;
		}
		
		public String getName(){
			return name;
		}
	}
	
}
