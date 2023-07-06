import Candidate from "@/components/Candidate";
import TextInput from "@/components/TextInput";
import Image from "next/image";

function create() {
  return (
    <div className="flex flex-col gap-8 px-16 pt-4">
      <TextInput placeholder="게임의 제목을 입력해주세요" />
      <div className="flex gap-32">
        <Candidate color="red" />
        <Candidate color="blue" />
      </div>
      <Image
        src="/assets/VSIcon.svg"
        alt="vs"
        height={80}
        width={80}
        className="absolute top-80 left-1/2 -translate-x-1/2"
      />
    </div>
  );
}

export default create;
