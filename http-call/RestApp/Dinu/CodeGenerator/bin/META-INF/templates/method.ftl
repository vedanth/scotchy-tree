<#ftl strip_whitespace=true strip_text=true ns_prefixes={"D":"http://treetechnologies.net/xml/ns/persistence/orm"}>

<#macro @element></#macro>
<#macro @text></#macro>

<#import "general-functions.ftl" as gf>
<#import "jpa-functions.ftl" as jpa>
<#import "generic-annotation.ftl" as ga>
<#import "method-m2o.ftl" as m2o>
<#import "method-o2m.ftl" as o2m>
<#import "method-m2m.ftl" as m2m>
<#import "method-m2m-ex.ftl" as m2mex>

<#macro "java-attributes"><#recurse></#macro>

<#macro id>
    <#assign association=.node>
    <#switch targetType>
        <#case "entityClass">
            <#recurse>
            <#break>
        <#case "attributesClass">
            <#break>
        <#case "idClass">
            <#recurse>
            <#break>
    </#switch>
</#macro>

<#macro basic>
    <#assign association=.node>
    <#switch targetType>
        <#case "entityClass">
            <#recurse>
            <#break>
        <#case "attributesClass">
            <#recurse>
            <#break>
        <#case "idClass">
            <#break>
    </#switch>
</#macro>

<#macro version>
    <#assign association=.node>
    <#switch targetType>
        <#case "entityClass">
            <#recurse>
            <#break>
        <#case "attributesClass">
            <#recurse>
            <#break>
        <#case "idClass">
            <#break>
    </#switch>
</#macro>

<#macro "many-to-one">
    <#switch targetType>
        <#case "entityClass">
            <#assign entity=entity in m2o>
            <#assign association=.node in m2o>
            <#recurse using m2o>
            <#break>
        <#case "attributesClass">
            <#if !gf.isManyToManyComponent(.node)>
                <#assign entity=entity in m2o>
                <#assign association=.node in m2o>
                <#recurse using m2o>
            </#if>
            <#break>
        <#case "idClass">
            <#break>
    </#switch>
</#macro>

<#macro "one-to-many">
    <#switch targetType>
        <#case "entityClass">
            <#assign entity=entity in o2m>
            <#assign association=.node in o2m>
            <#recurse using o2m>
            <#break>
        <#case "attributesClass">
            <#assign entity=entity in o2m>
            <#assign association=.node in o2m>
            <#recurse using o2m>
            <#break>
        <#case "idClass">
            <#break>
    </#switch>
</#macro>

<#macro "many-to-many">
    <#switch targetType>
        <#case "entityClass">
            <#assign entity=entity in m2m>
            <#assign association=.node in m2m>
            <#recurse using m2m>
            <#break>
        <#case "attributesClass">
            <#assign entity=entity in m2m>
            <#assign association=.node in m2m>
            <#recurse using m2m>
            <#break>
        <#case "idClass">
            <#break>
    </#switch>
</#macro>

<#macro "many-to-many-ex">
    <#switch targetType>
        <#case "entityClass">
            <#assign entity=entity in m2mex>
            <#assign association=.node in m2mex>
            <#recurse using m2mex>
            <#break>
        <#case "attributesClass">
            <#assign entity=entity in m2mex>
            <#assign association=.node in m2mex>
            <#recurse using m2mex>
            <#break>
        <#case "idClass">
            <#break>
    </#switch>
</#macro>

<#macro getter>
    <#recurse using ga>
    ${gf.methodScope(.node)} ${gf.type(association)} ${gf.methodName(.node)}()
    {
        return this.${gf.fieldName(association)};
    }
    
</#macro>

<#macro setter>
    <#recurse using ga>
    ${gf.methodScope(.node)} void ${gf.methodName(.node)}(${gf.type(association)} arg)
    {
        this.${gf.fieldName(association)} = arg;
    }
    
</#macro>
