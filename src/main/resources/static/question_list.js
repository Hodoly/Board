const page_elements = document.getElementsByClassName("page-link");
Array.from(page_elements).forEach(function(e) {
	e.addEventListener('click', function() {
		document.getElementById('page').value = this.dataset.page;
		document.getElementById('searchForm').submit();
	});
});
const btn_search = document.getElementById("btn_search");
btn_search.addEventListener('click', function() {
	document.getElementById('ct').value = document.getElementById('category').value;
	document.getElementById('kw').value = document.getElementById('search_kw').value;
	document.getElementById('page').value = 0; //검색버튼을 클릭할 경우 0페이지부터 조회한다.
	document.getElementById('searchForm').submit();
});

const select_category = document.getElementById("category");
select_category.addEventListener('change', function() {
	document.getElementById('page').value = 0;
	document.getElementById('kw').value = "";
	document.getElementById('ct').value = document.getElementById('category').value;
	document.getElementById('searchForm').submit();
});