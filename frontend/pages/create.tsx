import TextInput from "@/components/TextInput";
import TextareaInput from "@/components/TextareaInput";

function create() {
  return (
    <div>
      <TextInput placeholder="입력하세요" color="red" />
      <TextareaInput
        placeholder="한마디 남기기"
        maxLength={512}
        height="h-24"
      />
    </div>
  );
}

export default create;
