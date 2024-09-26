<#import "layout.ftlh" as main>
<@main.layout>

    <link rel="stylesheet" href="../css/search.css">

    <div class="container">
        <h2>Search Results</h2>
        <ul>
            <#list users as user>
                <li>
                    <a href="/profile/${user.id}">${user.name} (${user.nickName}) - ${user.email}</a>
                </li>
            </#list>
        </ul>
    </div>

</@main.layout>
