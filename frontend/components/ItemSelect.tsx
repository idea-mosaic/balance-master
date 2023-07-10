import { BACKGROUND_COLORS, BORDER_COLORS } from "@/constants/color";

interface Props {
  selectColor: "red" | "blue";
  selectImage?: string;
  selectTitle?: string;
  selectDesc?: string;
  selectRatio?: number;
}

function ItemSelect({
  selectColor,
  selectImage,
  selectTitle,
  selectDesc,
  selectRatio,
}: Props) {
  return (
    <div
      className={`w-1/4 h-60 rounded-3xl border-4 ${BACKGROUND_COLORS[selectColor]} ${BORDER_COLORS[selectColor]}`}
      style={
        selectImage
          ? {
              backgroundImage: `linear-gradient( rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4) ), url(${selectImage})`,
              backgroundPosition: "center",
              backgroundSize: "cover",
            }
          : {}
      }
    >
      <div className={"text-center text-white font-extrabold text-7xl mt-10"}>{selectTitle}</div>
      <div className={"text-center text-white font-bold text-4xl mt-5"}>{selectRatio ? `${selectRatio}%` : selectDesc}</div>
    </div>
  );
}

export default ItemSelect;
