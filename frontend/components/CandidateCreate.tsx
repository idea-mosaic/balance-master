import ImageInput from "./ImageInput";
import TextInput from "./TextInput";
import TextareaInput from "./TextareaInput";

interface Props {
  color: "red" | "blue";
}

function CandidateCreate({ color }: Props) {
  return (
    <div className="flex flex-col gap-4 w-full">
      <ImageInput color={color} />
      <TextInput color={color} placeholder="제목을 입력해주세요" />
      <TextareaInput
        color={color}
        placeholder="간단한 설명을 입력해주세요"
        height="h-24"
      />
    </div>
  );
}

export default CandidateCreate;
