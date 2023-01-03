import React, {useEffect, useState} from 'react';
import axios from 'axios';


const Hello = () => {
	const [hello, setHello] = useState('')

	useEffect(() => {
		axios.get('/api/test/hello')
			.then(response => setHello(response.data.data))
			.catch(error => console.log(error))
	}, []);

	return (
		<>
			백엔드에서 가져온 데이터입니다 : {hello}
		</>
	);
}

export default Hello;