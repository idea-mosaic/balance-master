import Candidate from "@/components/Candidate";
import TextInput from "@/components/TextInput";

function create() {
  return (
    <div className="flex flex-col gap-4 px-16 pt-4">
      <TextInput placeholder="게임의 제목을 입력해주세요" />
      <div className="flex gap-36">
        <Candidate color="red" />
        <Candidate color="blue" />
      </div>
    </div>
  );
}

export default create;
