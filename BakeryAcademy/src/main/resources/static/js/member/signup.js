
// 필수 입력 필드의 입력 상태를 실시간으로 확인
var requiredInputs = document.querySelectorAll(".sign-input input[required]");
for (var i = 0; i < requiredInputs.length; i++) {
	requiredInputs[i].addEventListener("input", checkRequiredFields);
}

// '모두 동의' 체크박스에 대한 이벤트 리스너를 설정하는 부분입니다. 
// '모두 동의' 체크박스가 선택되면 모든 개별 동의 체크박스도 선택됩니다.
const agreeChkAll = document.querySelector("input[name=agree_all]");
const agreeChk = document.querySelectorAll("input[name=agree]");

agreeChkAll.addEventListener("change", (e) => {
	for (let i = 0; i < agreeChk.length; i++) {
		agreeChk[i].checked = e.target.checked;
	}
});

// 각 개별 동의 체크박스가 모두 선택되면 '모두 동의' 체크박스도 선택되고, 
// 하나라도 선택되지 않으면 '모두 동의' 체크박스는 선택 해제됩니다.
for (let i = 0; i < agreeChk.length; i++) {
	agreeChk[i].addEventListener("change", () => {
		let allChecked = true;
		for (let j = 0; j < agreeChk.length; j++) {
			if (!agreeChk[j].checked) {
				allChecked = false;
				break;
			}
		}
		agreeChkAll.checked = allChecked;
	});
}

// 필수 입력 필드의 입력 상태를 검사하여 회원가입 버튼 활성화/비활성화
function checkRequiredFields() {
	var requiredInputs = document.querySelectorAll(".sign-input input[required]");
	var signupBtn = document.getElementById("signupBtn");
	for (var i = 0; i < requiredInputs.length; i++) {
		if (requiredInputs[i].value === "") {
			signupBtn.disabled = true;
			return;
		}
	}
	signupBtn.disabled = false;
}

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
		tmp += str.substr(3, 4);
		tmp += '-';
		tmp += str.substr(7);
		return tmp;
	} else { // 숫자의 총 개수가 11개를 초과하면 11개만 반환합니다.
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 4);
		tmp += '-';
		tmp += str.substr(7, 4);
		return tmp;
	}
}

// 핸드폰번호 입력칸 요소를 가져옵니다.
var phoneNum = document.getElementById('phoneNum');

// 핸드폰번호 입력칸에 키를 누를 때마다 자동으로 하이픈을 추가하는 이벤트 리스너를 등록합니다.
phoneNum.onkeyup = function() {
	this.value = autoHypenPhone(this.value);
}

// '회원가입' 버튼을 클릭하면 필드 값을 검증하고, 
// 모든 필드가 유효하면 폼을 제출하고 로그인 페이지로 리디렉션하는 함수입니다.
function validateAndRedirect(event) {

	event.preventDefault();
	// 필수 입력 필드들의 값을 가져옴
	var memberName = document.querySelector('.sign-info input[name="memberName"]').value;
	var memberId = document.querySelector('.sign-info input[name="memberId"]').value;
	var memberPassword = document.querySelector('.sign-info input[name="memberPassword"]').value;
	var memberPasswordConfirm = document.querySelector('.sign-info input[name="memberPasswordConfirm"]').value;
	var memberNickName = document.querySelector('.sign-info input[name="memberNickName"]').value;
	var memberEmail = document.querySelector('.sign-info input[name="memberEmail"]').value;
	var memberAddress = document.querySelector('.sign-info input[name="memberAddress"]').value;
	var memberPhoneNumber = document.querySelector('.sign-info input[name="memberPhoneNumber"]').value;

	// 각 필드에 대한 입력 확인
	if (memberName === "") {
		alert("이름을 입력해주세요.");
		return;
	}

	if (memberId === "") {
		alert("아이디를 입력해주세요.");
		return;
	}

	if (memberPassword === "") {
		alert("비밀번호를 입력해주세요.");
		return;
	}

	if (memberPasswordConfirm === "") {
		alert("비밀번호 확인을 입력해주세요.");
		return;
	}

	if (memberNickName === "") {
		alert("닉네임을 입력해주세요.");
		return;
	}

	if (memberEmail === "") {
		alert("이메일을 입력해주세요.");
		return;
	}

	if (memberAddress === "") {
		alert("주소를 입력해주세요.");
		return;
	}

	if (memberPhoneNumber === "") {
		alert("전화번호를 입력해주세요.");
		return;
	}
	// 비밀번호와 비밀번호 확인이 일치하는지 확인
	if (memberPassword !== memberPasswordConfirm) {
		alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		return;
	}

	// 이름 유효성 검사 (예: (한글 두글자 이상 입력)
	var nameRegex = /^[가-힣]{2,}$/;
	if (!nameRegex.test(memberName)) {
		alert("한글을 두 글자 이상 입력해야 합니다.");
		return;
	}

	// 비밀번호 유효성 검사 (예: (영문 대소문자/숫자/특수문자 포함. 단 최소 6자 이상.)
	var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&^]).{6,}$/;
	if (!passwordRegex.test(memberPassword)) {
		alert("비밀번호는 6자 이상이며, 최소 하나의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
		return;
	}

	//이메일 유효성 검사
	var emailRegex = /@/;
	if (!emailRegex.test(memberEmail)) {
		alert("이메일 형식이 올바르지 않습니다. 이메일 주소에는 '@' 문자가 포함되어야 합니다.");
		return;
	}
	// 전화번호 유효성 검사
	var phoneRegex = /^010-[0-9]{4}-[0-9]{4}$/;
	if (!phoneRegex.test(memberPhoneNumber)) {
		alert("전화번호는 '010-0000-0000' 형식으로 입력해야 합니다.");
		return;
	}

	// 약관 동의 여부 확인
	if (!agreeChkAll.checked) {
		alert("약관에 동의해주세요.");
		return;
	}

	// 아이디 중복 체크
	$.ajax({
		url: '/member/idcheck', //Controller에서 요청 받을 주소
		data: { 'memberId': memberId },
		success: function(result) {
			if (result == 'true') {

			} else {
				// 아이디가 중복되었을 때 오류 메시지를 표시합니다.
				alert('이미 존재하는 아이디입니다.');
				return;
			}
		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			return;
		}
	});

	// 닉네임 중복 체크
	$.ajax({
		url: '/member/nicknamecheck', //Controller에서 요청 받을 주소
		data: { 'memberNickName': memberNickName },
		success: function(result) {
			console.log("nicknamecheck", result);
			if (result == 'true') {
				
			} else {
				// 닉네임이 존재하였을 때, 오류 메시지 표시
				alert('이미 존재하는 닉네임 입니다.');
				return;
			}
		},
		error: function(request, status, error) {
			console.log("Error", request, status, error);
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			return;
		}
	});

	// 전화번호 중복 체크
	/*$.ajax({
		url: '/member/phonenumbercheck', //Controller에서 요청 받을 주소
		data: { 'memberPhoneNumber': memberPhoneNumber },
		success: function(result) {
			if (result == 'true') {

			} else {
				// 전화번호가 존재하였을 때, 오류 메시지 표시
				alert('이미 존재하는 전화번호 입니다.');
				return;
			}
		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			return;
		}
	});
*/
	// 이메일 중복 체크
/*	$.ajax({
		url: '/member/emailcheck', //Controller에서 요청 받을 주소
		data: { 'memberEmail': memberEmail },
		success: function(result) {
			if (result == 'true') {

			} else {
				// 이메일이 존재하였을 때, 오류 메시지 표시
				alert('이미 존재하는 이메일 입니다.');
				return;
			}
		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			return;
		}
	});*/

	// AJAX 요청 코드
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/member/signup", true);

	// 폼 데이터를 서버에 전송 (이 예제에서는 AJAX를 사용)
	var formData = new FormData();
	formData.append('memberName', memberName);
	formData.append('memberId', memberId);
	formData.append('memberPassword', memberPassword);
	formData.append('memberPasswordConfirm)', memberPasswordConfirm);
	formData.append('memberNickName', memberNickName);
	formData.append('memberEmail', memberEmail);
	formData.append('memberAddress', memberAddress);
	formData.append('memberPhoneNumber', memberPhoneNumber);




	xhr.onload = function() {
		if (xhr.status === 200) {
			// 회원가입 완료 메시지 표시 후 로그인 페이지로 리다이렉트
			alert("회원가입이 정상적으로 완료되었습니다.");
			window.location.href = '/member/login';
		} else {
			// 에러 메시지 표시
			alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
		}
	}

	xhr.send(formData);
}

window.onload = function() {
	document.getElementById("signupBtn").addEventListener("click", function(event) {
		validateAndRedirect(event);
	});
}