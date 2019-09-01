<html>
<head>
    <title>Mycat - 测试页面</title>
    <link href="${request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body style="margin-top:50px;overflow: hidden;">
<form action="${request.contextPath}/employee/save" method="post">
    <input type="hidden" name="isNew" value="<#if employee.isNew??>${employee.isNew}</#if>"/>
    <table class="gridtable" style="width:800px;">
        <tr>
            <th colspan="7">employee信息 - [<a href="${request.contextPath}/employee">返回</a>]</th>
        </tr>
        <tr>
        	<th>id：</th>
            <td><input type="text" name="id" value="<#if employee.id??>${employee.id?c}</#if>"/>
            </td>
            
            <th>名称：</th>
            <td><input type="text" name="name" value="<#if employee.name??>${employee.name}</#if>"/>
            </td>
            
            <th>分片ID：</th>
            <td><input type="text" name="shardingId" value="<#if employee.shardingId??>${employee.shardingId}</#if>"/>
            </td>
            <td><input type="submit" value="保存"/></td>
        </tr>
    <#if msg??>
        <tr style="color:#00ba00;">
            <th colspan="7">${msg}</th>
        </tr>
    </#if>
    </table>
</form>
</body>
</html>
