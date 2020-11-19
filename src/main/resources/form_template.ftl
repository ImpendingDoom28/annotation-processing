<form action="${form.action}" method="${form.method}">
    <#list form.inputs as input>
            <label>${input.name}</label>
            <input id="${input.name}" name="${input.name}" placeholder="${input.placeholder}" type="${input.type}" />
    </#list>
</form>