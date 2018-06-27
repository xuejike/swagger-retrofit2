<#-- @ftlvariable name="tool" type="Tool" -->
<#-- @ftlvariable name="api" type="doc.Api" -->

package com.bidanet.zhuyu.service;

import com.bidanet.android.common.vo.JavaApi;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ${clsName} {

    <#list apis as api >

        <#if api.get??>
        /**
         *${api.get.summary}
         */
        @GET("${api.url}")
        Observable<JavaApi> ${api.name}(
        <#if api.get.parameters??>
            <#list api.get.parameters as p>
                @Query("${p.name}") ${tool.typeMap(p.type)} ${p.name} <#if p_has_next>,</#if>
            </#list>
        </#if>
            );
        </#if>
        <#if api.post??>
        /**
         *${api.post.summary}
         */
         @POST("${api.url}")
         Observable<JavaApi> ${api.name}(
            <#if api.post.parameters??>
                <#list api.post.parameters as p>
                @Query("${p.name}") ${tool.typeMap(p.type)} ${p.name} <#if p_has_next>,</#if>
                </#list>
            </#if>);
        </#if>
    </#list>
}
