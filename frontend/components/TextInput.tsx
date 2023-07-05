interface Props {
  placeholder?: string;
  color?: "red" | "blue";
}

function TextInput({ placeholder, color }: Props) {
  const colors = {
    red: "border-mainColor1",
    blue: "border-mainColor2",
  };
  const borderColor = color ? colors[color] : "border-mainColor3";
  return (
    <input
      type="text"
      className={`border rounded-lg p-2 w-full ${borderColor}`}
      placeholder={placeholder}
    />
  );
}

export default TextInput;
