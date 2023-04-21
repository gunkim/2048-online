const createRoomBtn = document.querySelector('.create-room-btn');
const modal = document.querySelector('.modal');
const closeBtn = document.querySelector('.close');
const form = document.querySelector('form');

// 방 만들기 버튼 클릭 시 모달 열기
createRoomBtn.addEventListener('click', () => {
  modal.style.display = 'block';
});

// 모달 닫기 버튼 클릭 시 모달 닫기
closeBtn.addEventListener('click', () => {
  modal.style.display = 'none';
});

// 모달 바깥을 클릭하면 모달 닫기
window.addEventListener('click', (event) => {
  if (event.target == modal) {
    modal.style.display = 'none';
  }
});

// 방 만들기 폼 제출 시 처리
form.addEventListener('submit', (event) => {
  event.preventDefault(); // 폼 제출 이벤트 취소

  // 입력한 방 이름과 방장 이름 가져오기
  const roomName = document.getElementById('room-name').value;
  const ownerName = document.getElementById('owner-name').value;

  // 방 생성 후 목록에 추가하는 코드 작성하기
  // ...

  // 모달 닫기
  modal.style.display = 'none';
});
