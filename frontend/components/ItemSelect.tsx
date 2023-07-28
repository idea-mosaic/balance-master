import { BACKGROUND_COLORS, BORDER_COLORS } from "@/constants/color";

interface Props {
  selectColor: "red" | "blue";
  selectImage?: string;
  selectTitle?: string;
  selectDesc?: string;
  selectRatio?: number;
  clickEvent?: () => void;
}

function ItemSelect({
  selectColor,
  selectImage,
  selectTitle,
  selectDesc,
  selectRatio,
  clickEvent,
}: Props) {
  return (
    <div
      className={`w-full h-96 rounded-3xl border-4 ${BACKGROUND_COLORS[selectColor]} ${BORDER_COLORS[selectColor]}`}
      style={
        selectImage
          ? {
              backgroundImage: `linear-gradient( rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4) ), url(${selectImage})`,
              backgroundPosition: "center",
              backgroundSize: "cover",
            }
          : {}
      }
      onClick={clickEvent}
    >
      <div className="h-96 flex flex-col justify-center gap-4">
        <div className={"text-center text-white font-extrabold text-7xl"}>
          {selectTitle}
        </div>
        <div className={"text-center text-white font-bold text-4xl"}>
          {selectRatio ? `${selectRatio}%` : selectDesc}
        </div>
      </div>
    </div>
  );
}

export default ItemSelect;
