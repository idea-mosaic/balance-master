interface Props {
  placeholder?: string;
  color?: "red" | "blue";
  maxLength?: number;
}

function TextInput({ placeholder, color, maxLength }: Props) {
  const colors = {
    red: "border-mainColor1",
    blue: "border-mainColor2",
  };
  const borderColor = color ? colors[color] : "border-mainColor3";
  return (
    <input
      type="text"
      className={`border-2 rounded-lg p-2 w-full ${borderColor}`}
      placeholder={placeholder}
      maxLength={maxLength}
    />
  );
}

export default TextInput;
