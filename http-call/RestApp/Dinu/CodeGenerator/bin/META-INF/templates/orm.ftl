<#ftl strip_whitespace=true strip_text=true ns_prefixes={"D":"http://treetechnologies.net/xml/ns/persistence/orm"}>

<#macro @element></#macro>
<#macro @text></#macro>
<#import "entity.ftl" as entity>

<#recurse doc>

<#macro "mapping-files"><#recurse></#macro>

<#macro "entity-mappings">
    <#global targetType="entityClass">
    <#recurse using entity/>
    <#global targetType="attributesClass">
    <#recurse using entity/>
    <#global targetType="idClass">
    <#recurse using entity/>
</#macro>
