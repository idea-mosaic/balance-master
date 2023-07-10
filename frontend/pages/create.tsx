import CandidateCreate from "@/components/CandidateCreate";
import PasswordPostSubmit from "@/components/PasswordPostSubmit";
import TextInput from "@/components/TextInput";
import Image from "next/image";

function create() {
  return (
    <div className="px-16 pt-4">
      <div className="flex flex-col gap-8">
        <TextInput placeholder="게임의 제목을 입력해주세요" />
        <div className="flex gap-32">
          <CandidateCreate color="red" />
          <CandidateCreate color="blue" />
        </div>
        <Image
          src="/assets/VSIcon.svg"
          alt="vs"
          height={80}
          width={80}
          className="absolute top-80 left-1/2 -translate-x-1/2"
        />
      </div>
      <div className="mt-2 flex justify-end">
        <PasswordPostSubmit />
      </div>
    </div>
  );
}

export default create;
