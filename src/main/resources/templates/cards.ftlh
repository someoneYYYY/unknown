<!DOCTYPE html>
<html>
<head>
    <title>Cities</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>购物卡列表</h2>

<table>
    <tr>
        <th width="10%">店家</th>
        <th width="20%">购物卡</th>
        <th width="5%">面值</th>
        <th width="5%">金额</th>
        <th width="5%">库存</th>
        <th width="20%">链接</th>
        <th width="40%">说明</th>
    </tr>

    <#list cards as card>
        <tr>
            <td>${card.origin}</td>
            <td>${card.name}</td>
            <td>${card.standardPrice}</td>
            <td>${card.price}</td>
            <td>${card.stock}</td>
            <td><a href=${card.originUrl}>商铺链接</a></td>
            <td>${(card.description)!"默认"}</td>
        </tr>
    </#list>
</table>

</body>
</html>