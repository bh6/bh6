package com.ibm.bh6.rest;

import javax.ws.rs.core.Response;

public abstract class CORSResponse extends Response {

	public static Response.ResponseBuilder ok(java.lang.Object entity) {
		return Response.ok(entity).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600");
	}
}
