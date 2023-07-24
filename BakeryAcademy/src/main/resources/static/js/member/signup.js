
// 핸드폰번호 입력칸에 자동 하이픈 기능을 추가한 함수
var autoHypenPhone = function(str){
    str = str.replace(/[^0-9]/g, ''); // 입력된 문자열에서 숫자만 추출하여 저장합니다.
    var tmp = '';

    // 입력된 숫자에 따라 하이픈을 추가한 형식으로 변환합니다.
    if (str.length < 4){
        return str;
    } else if (str.length < 7){
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3);
        return tmp;
    } else if (str.length < 11){
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
phoneNum.onkeyup = function(){
    this.value = autoHypenPhone(this.value);  
}



// 동의 모두선택 / 해제
const agreeChkAll = document.querySelector("input[name=agree_all]");
const agreeChk = document.querySelectorAll("input[name=agree]");

agreeChkAll.addEventListener("change", (e) => {
  for (let i = 0; i < agreeChk.length; i++) {
    agreeChk[i].checked = e.target.checked;
  }
});

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

// 회원가입 버튼 클릭 이벤트 핸들러
document
  .getElementById("signupBtn")
  .addEventListener("click", function () {
   

   

    // 필수 입력 필드들의 값을 가져옴
	var memberName = document.querySelector('.sign-info input[name="memberName"]').value;
  var memberId = document.querySelector('.sign-info input[name="memberId"]').value;
  var memberPassword = document.querySelector('.sign-info input[name="memberPassword"]').value;
  var memberPasswordConfirm = document.querySelector('.sign-info input[name="memberPasswordConfirm"]').value;
  var memberNickName = document.querySelector('.sign-info input[name="memberNickName"]').value;
  var memberEmail = document.querySelector('.sign-info input[name="memberEmail"]').value;
  var memberAddress = document.querySelector('.sign-info input[name="memberAddress"]').value;
  var memberPhoneNumber = document.querySelector('.sign-info input[name="memberPhoneNumber"]').value;
   
	

    // 필수 입력 필드가 비어있는지 확인
    if (
      memberName === "" ||
      memberId === "" ||
      memberPassword === "" ||
      memberPasswordConfirm === "" ||
	  memberNickName  === "" ||
      memberEmail === "" ||
      memberAddress === "" ||
	  memberPhoneNumber === "" 
    ) {
	alert("모든 필수 입력 사항을 입력해주세요.");
      return;
    }
	
    

    // 비밀번호와 비밀번호 확인이 일치하는지 확인
    if (memberPassword !== memberPasswordConfirm) {
      alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      return;
    }

  

	 // 약관 동의 여부 확인
    if (!agreeChkAll.checked) {
      alert("약관에 동의해주세요.");
      return;
    }

    // 회원가입 완료 메시지 표시
    alert("회원가입이 정상적으로 완료되었습니다.");

    // 로그인 페이지로 이동
    window.location.href = "./login.html";
  });

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

// 필수 입력 필드의 입력 상태를 실시간으로 확인
var requiredInputs = document.querySelectorAll(".sign-input input[required]");
for (var i = 0; i < requiredInputs.length; i++) {
  requiredInputs[i].addEventListener("input", checkRequiredFields);
}
