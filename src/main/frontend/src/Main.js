import React from 'react';
import { Link } from 'react-router-dom';

const Main = (props) => {
	return (
		<>
			<h3>안녕하세요. 메인페이지 입니다.</h3>
				<div><Link to="/product/1">1번상품</Link></div>
				<div><Link to="/product/2">2번상품</Link></div>
				<div><Link to="/hello">Api</Link></div>
				<div><Link to="/loginPage">로그인</Link></div>
		</>
	);
};

export default Main;