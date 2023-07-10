import React from "react";

function Comment() {
  return (
    <div className="px-4">
      <div className="flex justify-between text-xs">
        <div>익명 2021.01.03 05:00</div>
        <div className="flex gap-2">
          <div>수정</div>|<div>삭제</div>
        </div>
      </div>
      <div>짬뽕이 진짜 맛있음</div>
    </div>
  );
}

export default Comment;
