<%--分页列表--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<script id="paginateTemplate" type="x-tmpl-mustache">
<div class="col-xs-6">
    <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">
        总共 {{total}} 中的 {{from}} ~ {{to}}
    </div>
</div>
    
<div class="col-xs-6">
    <div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
        <ul class="pagination">
            <li class="paginate_button previous {{^firstUrl}}disabled{{/firstUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-target="1" data-url="{{firstUrl}}" class="page-action">首页</a>
            </li>
            <li class="paginate_button {{^beforeUrl}}disabled{{/beforeUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-target="{{beforePageNo}}" data-url="{{beforeUrl}}" class="page-action">前一页</a>
            </li>
            <li class="paginate_button active" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-id="{{pageNo}}" >第{{pageNo}}页</a>
                <input type="hidden" class="pageNo" value="{{pageNo}}" />
            </li>
            <li class="paginate_button {{^nextUrl}}disabled{{/nextUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-target="{{nextPageNo}}" data-url="{{nextUrl}}" class="page-action">后一页</a>
            </li>
            <li class="paginate_button next {{^lastUrl}}disabled{{/lastUrl}}" aria-controls="dynamic-table" tabindex="0">
                <a href="#" data-target="{{maxPageNo}}" data-url="{{lastUrl}}" class="page-action">尾页</a>
            </li>
        </ul>
    </div>
</div>
</script>

<script type="text/javascript">

    var paginateTemplate = $("#paginateTemplate").html();
    Mustache.parse(paginateTemplate);

    //分页函数
    //请求链接、当前要求满足的行数、当前第几页、每页显示数目、、当前页返回结果数目、值所存储的元素、回调方法
    function renderPage(url, total, pageNo, pageSize, currentSize, idElement, callback) {
        var maxPageNo = Math.ceil(total / pageSize);//最大的页数
        var paramStartChar = url.indexOf("?") > 0 ? "&" : "?";//拼接实际请求url
        var from = (pageNo - 1) * pageSize + 1;//每页从第几条开始
        var view = {
            from: from > total ? total : from,
            to: (from + currentSize - 1) > total ? total : (from + currentSize - 1),
            total : total,//满足要求的行数
            pageNo : pageNo,//页码数
            maxPageNo : maxPageNo,//最大页码数
            nextPageNo: pageNo >= maxPageNo ? maxPageNo : (pageNo - 0 + 1),//下一页
            beforePageNo : pageNo == 1 ? 1 : (pageNo - 1),//上一页
            firstUrl : (pageNo == 1) ? '' : (url + paramStartChar + "pageNo=1&pageSize=" + pageSize),//首页url
            beforeUrl: (pageNo == 1) ? '' : (url + paramStartChar + "pageNo=" + (pageNo - 1) + "&pageSize=" + pageSize),//上一页url
            nextUrl : (pageNo >= maxPageNo) ? '' : (url + paramStartChar + "pageNo=" + (pageNo - 0 + 1) + "&pageSize=" + pageSize),//下一页url
            lastUrl : (pageNo >= maxPageNo) ? '' : (url + paramStartChar + "pageNo=" + maxPageNo + "&pageSize=" + pageSize)//尾页url
        };
        $("#" + idElement).html(Mustache.render(paginateTemplate, view));

        $(".page-action").click(function(e) {
            e.preventDefault();
            $("#" + idElement + " .pageNo").val($(this).attr("data-target"));
            var targetUrl  = $(this).attr("data-url");
            if(targetUrl != '') {
                $.ajax({
                    url : targetUrl,
                    success: function (result) {
                        if (callback) {
                            callback(result, url);
                        }
                    }
                })
            }
        })
    }
</script>
