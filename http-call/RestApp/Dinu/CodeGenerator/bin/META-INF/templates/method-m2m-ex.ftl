<#ftl strip_whitespace=true strip_text=true ns_prefixes={"D":"http://treetechnologies.net/xml/ns/persistence/orm"}>
<#macro @element></#macro>
<#macro @text></#macro>
<#import "general-functions.ftl" as gf>
<#import "generic-annotation.ftl" as ga>

<#macro "java-attributes"><#recurse></#macro>

<#macro "getter">
    <#recurse using ga>
    ${gf.methodScope(.node)} java.util.Set<${peerClass(association)}> ${gf.methodName(.node)}()
    {
        return java.util.Collections.unmodifiableSet(this.${gf.fieldName(association)}.keySet());
    }
    
</#macro>

<#macro enquirer>
    <#recurse using ga>
    ${gf.methodScope(.node)} Boolean ${gf.methodName(.node)}(${peerClass(association)} arg)
    {
        return this.${gf.fieldName(association)}.keySet().contains(arg);
    }
    
</#macro>

<#macro "detail-getter">
    <#recurse using ga>
    <#local peerEntity = gf.findEntity(association,peerClass(association))/>
    <#local peerAssociation=gf.findAttribute(peerEntity,peerField(association))/>
    <#local associationEntity = gf.findEntity(association,gf.type(association))/>
    <#local associationAttributeClass=gf.packageName(associationEntity) + "." + gf.className(associationEntity["detail-class"])/>
    ${gf.methodScope(.node)} ${associationAttributeClass} ${gf.methodName(.node)}(${peerClass(association)} arg)
    {
        <#if ((association.@owning[0])!"false") = "true">
        if(this.${gf.fieldName(association)}.containsKey(arg))
            return this.${gf.fieldName(association)}.get(arg).get${gf.className(associationEntity["detail-class"])}();
        else
            return null;
        <#else>
        if(arg != null)
            return arg.${gf.methodName(peerAssociation["java-attributes"]["detail-getter"])}(this);
        else
            return null;
        </#if>
    }
    
</#macro>

<#macro "detail-setter">
    <#recurse using ga>
    <#local peerEntity = gf.findEntity(association,peerClass(association))/>
    <#local peerAssociation=gf.findAttribute(peerEntity,peerField(association))/>
    <#local associationEntity = gf.findEntity(association,gf.type(association))/>
    <#local associationAttributeClass=gf.packageName(associationEntity) + "." + gf.className(associationEntity["detail-class"])/>
    ${gf.methodScope(.node)} void ${gf.methodName(.node)}(${peerClass(association)} arg,${associationAttributeClass} arg1)
    {
        <#if ((association.@owning[0])!"false") = "true">
        if(this.${gf.fieldName(association)}.containsKey(arg) && arg1 != null)
            this.${gf.fieldName(association)}.get(arg).set${gf.className(associationEntity["detail-class"])}(arg1);
        <#else>
        if(arg != null && arg1 != null)
            arg.${gf.methodName(peerAssociation["java-attributes"]["detail-setter"])}(this,arg1);
        </#if>
    }

</#macro>

<#macro "adder">
    <#recurse using ga>
    <#local peerEntity = gf.findEntity(association,peerClass(association))/>
    <#local peerAssociation=gf.findAttribute(peerEntity,peerField(association))/>
    <#local associationEntity = gf.findEntity(association,gf.type(association))/>
    <#local associationAttributeClass=gf.packageName(associationEntity) + "." + gf.className(associationEntity["detail-class"])/>
    ${gf.methodScope(.node)} void ${gf.methodName(.node)}(${peerClass(association)} arg, ${associationAttributeClass} arg1,EntityManager em)
    {
        if(arg == null || this.${gf.fieldName(association)}.containsKey(arg))
            return;
        <#if ((association.@owning[0])!"false") = "true">
        ${gf.type(association)} obj = new ${gf.type(association)}();
        obj.set${gf.className(associationEntity["detail-class"])}(arg1);
        em.persist(obj);
        this.${gf.fieldName(association)}.put(arg,obj);  
        <#if peerAssociation[0]??>
        if(!arg.${gf.methodName(peerAssociation["java-attributes"].enquirer)}(this))
        {
            arg.${gf.methodName(peerAssociation["java-attributes"].adder)}(this,arg1,em);
        }
        </#if>
        <#else>
        if(!arg.${gf.methodName(peerAssociation["java-attributes"].enquirer)}(this))
        {
            arg.${gf.methodName(peerAssociation["java-attributes"].adder)}(this,arg1,em);
        }
        
        if(!this.${gf.fieldName(association)}.containsKey(arg))
        {
            ${gf.packageName(associationEntity)}.${gf.className(associationEntity["id-class"])} pk = new ${gf.packageName(associationEntity)}.${gf.className(associationEntity["id-class"])}(); 
            <#list entity.attributes.id as id>
            pk.${gf.methodName(id["java-attributes"].setter)}(this.${gf.methodName(id["java-attributes"].getter)}());
            </#list>
            <#list peerEntity.attributes.id as id>
            pk.${gf.methodName(id["java-attributes"].setter)}(arg.${gf.methodName(id["java-attributes"].getter)}());
            </#list>
            ${gf.packageName(associationEntity)}.${gf.className(associationEntity)} obj = em.find(${gf.packageName(associationEntity)}.${gf.className(associationEntity)}.class,pk);
            this.${gf.fieldName(association)}.put(arg,obj);
        }
        </#if>
    }
    
</#macro>

<#macro "remover">
    <#recurse using ga>
    <#local peerEntity = gf.findEntity(association,peerClass(association))/>
    <#local peerAssociation=gf.findAttribute(peerEntity,peerField(association))/>
    <#local associationEntity = gf.findEntity(association,gf.type(association))/>
    <#local associationAttributeClass=gf.className(associationEntity["detail-class"])/>
    ${gf.methodScope(.node)} void ${gf.methodName(.node)}(${peerClass(association)} arg,EntityManager em)
    {
        if(arg == null || !this.${gf.fieldName(association)}.containsKey(arg))
            return;
        <#if ((association.@owning[0])!"false") = "true">
        em.remove(this.${gf.fieldName(association)}.get(arg));
        </#if>
        this.${gf.fieldName(association)}.remove(arg);
        <#if peerAssociation[0]??>
        if(!arg.${gf.methodName(peerAssociation["java-attributes"].enquirer)}(this))
        {
            arg.${gf.methodName(peerAssociation["java-attributes"].remover)}(this,em);
        }
        </#if>
    }
    
</#macro>

<#function peerClass association>
    <#return gf.qualifiedName(association.@peer[0])>
</#function>

<#function peerField association>
    <#return association["@peer-name"][0]>
</#function>
