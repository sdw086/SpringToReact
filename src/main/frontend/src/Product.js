import React from 'react';

const Product = (props) => {
	const current = decodeURI(window.location.href);
	const search = current.split("?")[1];
	const params = new URLSearchParams(search);
	const keywords = params.get('aa');
	console.log(params);
	console.log(keywords);

	return (
		<>
			<h3>상품 페이지입니다.</h3>
		</>
	);
}

export default Product;