interface Props {
  placeholder?: string;
  color?: "red" | "blue";
  maxLength?: number;
  height: string;
}

function TextareaInput({ placeholder, color, maxLength, height }: Props) {
  const colors = {
    red: "border-mainColor1",
    blue: "border-mainColor2",
  };
  const borderColor = color ? colors[color] : "border-mainColor3";

  return (
    <textarea
      className={`border rounded-lg p-2 w-full resize-none ${borderColor} ${height}`}
      placeholder={placeholder}
      maxLength={maxLength}
    />
  );
}

export default TextareaInput;
