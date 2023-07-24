// 핸드폰번호 입력칸에 자동 하이픈 기능을 추가한 함수
var autoHypenPhone = function(str) {
	str = str.replace(/[^0-9]/g, ''); // 입력된 문자열에서 숫자만 추출하여 저장합니다.
	var tmp = '';

	// 입력된 숫자에 따라 하이픈을 추가한 형식으로 변환합니다.
	if (str.length < 4) {
		return str;
	} else if (str.length < 7) {
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3);
		return tmp;
	} else if (str.length < 11) {
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 3);
		tmp += '-';
		tmp += str.substr(6);
		return tmp;
	} else {
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 4);
		tmp += '-';
		tmp += str.substr(7);
		return tmp;
	}
	return str;
}

// 핸드폰번호 입력칸 요소를 가져옵니다.
var phoneNum = document.getElementById('phoneNum');

// 핸드폰번호 입력칸에 키를 누를 때마다 자동으로 하이픈을 추가하는 이벤트 리스너를 등록합니다.
phoneNum.onkeyup = function() {
	this.value = autoHypenPhone(this.value);
}

var updateForm = document.getElementById('member-update');

updateForm.addEventListener('submit', function(e) {
	var memberPassword = document.getElementsByName('memberPassword')[0].value;
	var memberPasswordConfirm = document.getElementsByName('memberPasswordConfirm')[0].value;

	if (memberPassword !== memberPasswordConfirm) {
		alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		e.preventDefault(); // form 제출을 중단합니다.
	} else {
		alert("회원정보가 수정되었습니다."); // 회원 정보 수정이 성공하였음을 알립니다.
		window.location.href = "http://localhost:80";
		; // 회원 정보 수정이 성공하였을 때 홈페이지로 이동합니다.
	}
});
