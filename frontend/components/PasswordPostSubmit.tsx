import Button from "./Button";
import TextInput from "./TextInput";

function PasswordPostSubmit() {
  return (
    <div>
      <div className="flex  items-end gap-4">
        <div>
          <TextInput label="비밀번호" type="password" />
        </div>
        <Button
          clickEvent={function (): void {
            alert("CLICKED!!");
          }}
          content={"등록"}
          color={"main-color3"}
          height={"h-10"}
          width={"w-20"}
          borderRadius="rounded-xl"
          fontStyle="font-bold text-white"
        ></Button>
      </div>
      <div className="text-xs text-red-600 mt-1">
        * 추후 게시글 수정 및 삭제에 이용됩니다.
      </div>
    </div>
  );
}

export default PasswordPostSubmit;
