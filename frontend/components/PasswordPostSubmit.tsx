import Button from "./Button";
import TextInput from "./TextInput";

function PasswordPostSubmit() {
  return (
    <div>
      <TextInput label="비밀번호" type="password" />
      <div className="text-xs text-red-600 mt-1">
        * 추후 게시글 수정 및 삭제에 이용됩니다.
      </div>
    </div>
  );
}

export default PasswordPostSubmit;
