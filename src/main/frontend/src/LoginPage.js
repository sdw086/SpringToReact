import React, {useEffect, useState } from "react";
import axios from "axios";

const LoginPage = () => {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const onUsernameHandler = (event) => {
		setUsername(event.currentTarget.value);
	};

	const onPasswordHandler = (event) => {
		setPassword(event.currentTarget.value);
	};

	const btnClick = () => {
		console.log(username);
		console.log(password);

		axios.post('/api/member/login', JSON.stringify({
			username: username,
			passwd: password
		}) , {
			headers: {
				"Content-Type": `application/json`,
			}
		})
		.then(function (response) {
			console.log(response);
		})
		.catch(function (error) {
			console.log(error);
		});
	}

	return (
		<div>
			<form id="loginForm">
				<label>id</label>
				<input type="text" value={username} onChange={onUsernameHandler} />
				<br />
				<label>Password</label>
				<input type="password" value={password} onChange={onPasswordHandler} />
				<br />
				<button type="button" onClick={btnClick}>Login</button>
			</form>
		</div>
	);
};

export default LoginPage;