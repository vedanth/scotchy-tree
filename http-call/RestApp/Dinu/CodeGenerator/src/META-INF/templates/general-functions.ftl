<#ftl strip_whitespace=true strip_text=true ns_prefixes={"D":"http://treetechnologies.net/xml/ns/persistence/orm"}>

<#function packageName entity>
    <#return entity?parent.package>
</#function>

<#function className node>
    <#local retval = node.@class[0]>
    <#return retval>
</#function>

<#function fieldName node>
    <#local retval = node.@name[0]>
    <#return retval>
</#function>

<#function methodName method>
    <#local retval = (method.@name[0])!defaultMethodName(method)>
    <#return retval>
</#function>

<#function associationMethodName association methodType>
    <#local retval = (association["java-attributes"][methodType].@name[0])!defaultMethodName(association["java-attributes"][methodType])>
    <#return retval>
</#function>

<#function classScope node>
    <#local retval = (node["java-attributes"].@scope[0])!"public">
    <#return retval>
</#function>

<#function fieldScope node>
    <#local retval = (node["java-attributes"].@scope[0])!"protected">
    <#return retval>
</#function>

<#function methodScope node>
    <#local retval = (node.@scope[0])!"public">
    <#return retval>
</#function>

<#function qualifiedName class>
    <#local entity = findEntity(doc,class)/>
    <#if entity[0]??>
        <#local retval>${entity?parent.package[0]}.${class}</#local>
    <#else>
        <#local retval>${class}</#local>
    </#if>
    
    <#return retval/>
</#function>

<#function typeName node>
    <#local entity = findEntity(node,node["java-attributes"].@type[0])>
    <#local retval><#if entity[0]??>${entity?parent.package[0]}.${node["java-attributes"].@type[0]}<#else>${node["java-attributes"].@type[0]}</#if></#local>
    <#--<#local retval = node["java-attributes"].@type[0]>
    <#local entity = findEntity(node,retval)/>
    <#if entity[0]??>
        <#local retval>${entity?parent.package[0]}.${retval}</#local>
    </#if>
-->
    <#return retval/>
</#function>

<#function type node>
    <#local entity = findEntity(node,node["java-attributes"].@type[0])>
    <#local retval = node["java-attributes"].@type[0]>
    <#if entity[0]??>
        <#local retval>${entity?parent.package[0]}.${retval}</#local>
    </#if>
    
    <#return retval/>
</#function>

<#function field node>
    <#local retval>${fieldScope(node)} ${type(node)} ${fieldName(node)};</#local>
    <#return retval>
</#function>

<#function defaultMethodName method>
    <#switch method?node_name>
        <#case "getter">
            <#return "get" + fieldName(method?parent?parent)?cap_first>
        <#break>
        <#case "setter">
            <#return "set" + fieldName(method?parent?parent)?cap_first>
        <#break>
        <#case "adder">
            <#return "add" + fieldName(method?parent?parent)?cap_first>
        <#break>
        <#case "remover">
            <#return "remove" + fieldName(method?parent?parent)?cap_first>
        <#break>
        <#case "enquirer">
            <#return "has" + fieldName(method?parent?parent)?cap_first>
            <#break>
        <#case "acquirer">
            <#return "acquire" + fieldName(method?parent?parent)?cap_first>
            <#break>
        <#case "detail-getter">
            <#return "get" + fieldName(method?parent?parent)?cap_first + "Details">
            <#break>
        <#case "detail-setter">
            <#return "set" + fieldName(method?parent?parent)?cap_first + "Details">
            <#break>
    </#switch>
</#function>

<#function findEntity anyNode entityClassName>
    <#local className=entityClassName?substring((entityClassName?last_index_of("."))+1)>
    <#return anyNode["//D:entity[@class=$className]"]>
</#function>

<#function findAttribute entity attributeName>
    <#return entity["D:attributes/*[@name=$attributeName]"]>
</#function>

<#function isManyToManyComponent node>
    <#return ((node["java-attributes"]["@many-to-many-component"][0])!"false") = "true">
</#function>

<#function inverseAssociation association>
    <#local fqclassName = type(association)>
    <#local inverseClass = fqclassName?substring(fqclassName?last_index_of(".") + 1)>
    <#local fieldName = fieldName(association)>
    <#local inverseType="one-to-one">
    <#switch association?node_name>
        <#case "one-to-one">
            <#local inverseType="one-to-one">
            <#break>
        <#case "many-to-one">
            <#local inverseType="one-to-many">
            <#break>
        <#case "many-to-many">
            <#local inverseType="many-to-many">
            <#break>
        <#case "many-to-many-ex">
            <#local inverseType="many-to-many-ex">
            <#break>
    </#switch>
    <#local xpath>//D:entity[@class='${inverseClass}']/D:attributes/D:${inverseType}[@mapped-by='${fieldName}']</#local>
    <#local inverseAssociation = association[xpath]>
    <#return inverseAssociation>
</#function>

<#function owningAssociation association>
    <#local fqclassName = type(association)>
    <#local owningClass = fqclassName?substring(fqclassName?last_index_of(".") + 1)>
    <#local fieldName = association["@mapped-by"][0]>
    <#local owningType="one-to-one">
    <#switch association?node_name>
        <#case "one-to-one">
            <#local owningType="one-to-one">
            <#break>
        <#case "one-to-many">
            <#local owningType="many-to-one">
            <#break>
        <#case "many-to-many">
            <#local owningType="many-to-many">
            <#break>
        <#case "many-to-many-ex">
            <#local owningType="many-to-many-ex">
            <#break>
    </#switch>
    <#local xpath>//D:entity[@class='${owningClass}']/D:attributes/D:${owningType}[@name='${fieldName}']</#local>
    <#local owningAssociation = association[xpath]>
    <#return owningAssociation>
</#function>

<#function peerAssociation association>
    <#if isInverseAssociation(association)>
        <#return owningAssociation(association)>
    <#else>
        <#return inverseAssociation(association)>
    </#if> 
</#function>

<#function isInverseAssociation association>
    <#return (association["@mapped-by"][0]??)>
</#function>
