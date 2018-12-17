<#-- 业务类型 -->

<#--定义宏-->
<#macro dicMapStatuts dicMap status>
    <#if dicMap[status?string]??>
        <#compress>
            ${dicMap[status?string]?html}
        </#compress>
    </#if>
</#macro>

<#--下拉列表-->
<#macro selectOption options value="" >
    <#list options?keys as key>
        <option value="${key?html}"<@checkSelected key value/>>${options[key]?html}</option>
    </#list>
</#macro>

<#--li 下拉列表-->
<#macro liOption options value="" >
    <#list options?keys as key>
        <li  date-value="${key?html}"<@checkSelected key value/>>${options[key]?html}</li>
    </#list>
</#macro>
<#macro checkSelected key value>
    <#if key?is_number && key == value?number>selected="selected"</#if>
    <#if key?is_string && key == value>selected="selected"</#if>
</#macro>

<#--通用型的select语句-->
<#macro select id datas value="" defaultValue="" key="" text="" flag="">

<select id="${id}" name="${id}" class="select" ${flag}>
    <option value="">${defaultValue}</option>
    <#--判断对象是否为map-->
    <#if datas?is_hash_ex>
    <#--循环map的key值-->
        <#list datas?keys as key>
        <#--如果传进来的key值和默认的值相等，则选中这个值-->
            <#if key==value>
                <option value="${key}" selected>${datas[key]}</option>
            <#else>
                <option value="${key}">${datas[key]}</option>
            </#if>
        </#list>
    <#else>
        <#list datas as data>
        <#--如果key值不为空-->
            <#if key!="">
            <#--传进来的默认value和通过data的key取出来的值相等，则选中-->
                <#if value==data[key]?string>
                    <option value="${data[key]}" selected>${data[text]}</option>
                <#else>
                    <option value="${data[key]}" >${data[text]}</option>
                </#if>
            <#else>
                <#if data==value>
                    <option value="${data}" selected>${data}</option>
                <#else>
                    <option value="${data}">${data}</option>
                </#if>
            </#if>
        </#list>
    </#if>
    <select>
        </#macro>

        <#--页面格式化-->
        <#macro getValue datas value>
            <#list datas? keys as key>
                <#if value!="">
                    <#if key==value>
                        ${datas[key]}
                    <#else>

                    </#if>
                </#if>
            </#list>
        </#macro>
