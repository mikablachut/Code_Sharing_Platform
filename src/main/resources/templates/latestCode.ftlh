<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
    <link rel="stylesheet" href="/styles.css">
</head>
<body>
    <#list codes as code>
     <span id="load_date">${code.date}</span>
     <pre id="code_snippet">${code.code}</pre>
    </#list>
</body>
</html>