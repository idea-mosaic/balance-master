import React from "react";
import ItemSelect from "./ItemSelect";
import TextareaInput from "./TextareaInput";
import Button from "./Button";
import Comment from "./Comment";

interface Props {
  title: string;
  image?: string;
  desc: string;
  ratio?: number;
  color: "red" | "blue";
  clickEvent: () => void;
}
function CandidateVote({
  title,
  image,
  desc,
  ratio,
  color,
  clickEvent,
}: Props) {
  return (
    <div className="w-full flex flex-col gap-4">
      <ItemSelect
        selectColor={color}
        selectImage={image}
        selectTitle={title}
        selectRatio={ratio}
        selectDesc={desc}
        clickEvent={clickEvent}
      ></ItemSelect>
      <div>
        <TextareaInput
          color={color}
          placeholder="한마디 남기기"
          height="h-24"
        />
        <Button
          clickEvent={function (): void {
            alert("CLICKED!!");
          }}
          content={"저장하기"}
          color={"main-color3"}
          height={"h-12"}
          width={"w-full"}
          borderRadius="rounded-xl"
          fontStyle="font-bold text-white"
        ></Button>
      </div>
      <div className="flex flex-col gap-2">
        <Comment />
        <Comment />
        <Comment />
        <Comment />
      </div>
    </div>
  );
}

export default CandidateVote;
