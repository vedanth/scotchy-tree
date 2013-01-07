<#ftl strip_whitespace=true strip_text=true>

<#macro @element></#macro>
<#macro @text></#macro>

<#recurse doc>

<#macro "mapping-files"><#recurse></#macro>

<#macro schemas>
FILE custom.properties
<#recurse>
</#macro>

<#macro schema><#recurse></#macro>

<#macro table>
<#assign tableNode=.node>
${.node.@name}.class-name: ${tableName(.node)}
<#recurse>
</#macro>

<#macro column>
${tableNode.@name}.${.node.@name}.field-name: ${fieldName(.node)}
</#macro>

<#function tableName node>
    <#if node.@name?index_of("_") = 2>
        <#local start=3>
    <#else>
        <#local start=0>
    </#if>
    <#if node.@name[0]?substring(node.@name[0]?last_index_of("_")+1)?upper_case = "MASTER">
        <#local end=node.@name[0]?last_index_of("_")>
    <#else>
        <#local end=node.@name[0]?length>
    </#if>
    
    <#local retval = node.@name[0]?substring(start,end)?replace("_"," ")?capitalize?replace(" ","")/>
    <#local retval = retval?replace("Char","Characteristic")?replace("Spec","Specification")>
    
    <#return retval>
</#function>

<#function fieldName node>
    <#local retval=node.@name[0]?substring(0,.node.@name[0]?last_index_of("_"))>
    <#local retval=retval?replace("_"," ")?capitalize?replace(" ","")?uncap_first>
    <#local retval = retval?replace("char","characteristic")?replace("Char","Characteristic")>
    <#local retval = retval?replace("spec","specification")?replace("Spec","Specification")>
    <#local retval = retval?replace("seq","sequence")>
    <#if retval?ends_with("Id")>
        <#if !((((tableNode.pk.@column[0])!"#NONE#") = .node.@name[0]) || (((tableNode.pk.on.@column[0])!"#NONE#") = .node.@name[0]))>
            <#local retval = retval?substring(0,retval?length - 2)>
        </#if>
    </#if>
    <#return retval>
</#function>