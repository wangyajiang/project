package com.task.main;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.tool.utils.DTUtils;
import com.tool.utils.ElasticsearchUtils;

public class Test {

	public void set() {

		ElasticsearchUtils.newInstance();
		String type = "gpmapi_dev";
		String index = "organization";
		System.out.println(ElasticsearchUtils.getDocument(index, type, "1"));
		System.out.println(ElasticsearchUtils.getDocument(index, type, "100"));
		System.out.println(ElasticsearchUtils.getDocument(index, type, "101").get("createTime"));
		System.out.println(ElasticsearchUtils.getDocument(index, type, "102"));
		System.out.println("===============");
		TransportClient client = ElasticsearchUtils.getClient();
		SearchResponse actionGet = client.prepareSearch(index).setTypes(type)
                .setQuery(
                		QueryBuilders.boolQuery()
//                		.must(QueryBuilders.termQuery("parentId", "215"))
                		.should(QueryBuilders.termQuery("name", "玉皇山"))
                 )
                 .setFrom(0)
                 .setSize(3)
                 .execute().actionGet();
		
	    SearchHits hits = actionGet.getHits();
	    List<Map<String, Object>> matchRsult = new LinkedList<Map<String, Object>>();
	    SearchHit[] hitArr = hits.getHits();
	    for (SearchHit hit :  hitArr) {
	        matchRsult.add(hit.getSource());
	        System.out.println(hit.getSource());
	    }
	}
	public static void main(String[] args) {
		new Test().set();
		
	}
}
