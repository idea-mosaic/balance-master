import { BORDER_COLORS } from "@/constants/color";
import { readFile } from "@/utils/readFile";
import Image from "next/image";
import React, { useState, ChangeEvent } from "react";

interface Props {
  color: "red" | "blue";
}

const ImageInput = ({ color }: Props) => {
  const [previewImage, setPreviewImage] = useState<string>("");

  const handleImageChange = async (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];

    if (file) {
      try {
        const dataUrl = await readFile(file);
        setPreviewImage(dataUrl);
      } catch (error) {
        alert(error);
        setPreviewImage("");
      }
    }
  };

  return (
    <div
      className={`border-2 ${BORDER_COLORS[color]} w-full h-96 rounded-3xl flex justify-center items-center`}
      style={
        previewImage
          ? {
              backgroundImage: `url(${previewImage})`,
              backgroundPosition: "center",
              backgroundSize: "cover",
            }
          : {}
      }
    >
      <label htmlFor={`${color}imageInput`}>
        {previewImage ? (
          <div className="cursor-pointer border w-48 h-12 shadow-xl rounded-xl bg-white text-center flex items-center justify-center ">
            사진 변경하기
          </div>
        ) : (
          <Image
            className="cursor-pointer"
            src="/assets/addButtonIcon.svg"
            alt="addButton"
            height={80}
            width={80}
          />
        )}
      </label>
      <input
        id={`${color}imageInput`}
        type="file"
        accept="image/*"
        className="sr-only"
        onChange={handleImageChange}
      />
    </div>
  );
};

export default ImageInput;
