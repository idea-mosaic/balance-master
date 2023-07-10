interface Props {
  redImage?: string;
  blueImage?: string;
  redTitle?: string;
  blueTitle?: string;
  title: string;
  author: string;
  createDate: string;
}

function BoardItem({
  redImage,
  blueImage,
  redTitle,
  blueTitle,
  title,
  author,
  createDate,
}: Props) {
  return (
    <div className="flex flex-col w-80 m-0 cursor-pointer">
      <div className="flex h-36  hover:scale-110 transition-transform ease-in-out duration-700">
        <div
          className={`w-1/2 bg-mainColor1 border-mainColor1 border-4`}
          style={
            redImage !== ""
              ? {
                  clipPath: "polygon(0 0, 69% 0, 100% 100%, 0% 100%)",
                  borderRadius: "10% 10% 0 10%",
                  backgroundImage: `url(${redImage})`,
                  backgroundPosition: "center",
                  backgroundSize: "cover",
                }
              : {
                  clipPath: "polygon(0 0, 69% 0, 100% 100%, 0% 100%)",
                  borderRadius: "10% 10% 0 10%",
                }
          }
        >
          <p className="py-14 w-28 text-white font-bold text-xl text-center truncate">
            {redImage !== "" ? "" : redTitle}
          </p>
        </div>
        <div className="text-center text-mainColor4 font-black text-2xl -translate-x-8 z-10">
          <p className="bg-white w-10 my-14 rounded">VS</p>
        </div>
        <div
          className={`w-1/2 bg-mainColor2 border-mainColor2 border-4 -translate-x-16`}
          style={
            blueImage !== ""
              ? {
                  clipPath: "polygon(0 0, 100% 0, 100% 100%, 31% 100%)",
                  borderRadius: "0 10% 10% 10%",
                  backgroundImage: `url(${blueImage})`,
                  backgroundPosition: "center",
                  backgroundSize: "cover",
                }
              : {
                  clipPath: "polygon(0 0, 100% 0, 100% 100%, 31% 100%)",
                  borderRadius: "0 10% 10% 10%",
                }
          }
        >
          <p className="py-14 w-28 text-white font-bold text-xl text-center truncate translate-x-7">
            {blueImage !== "" ? "" : blueTitle}
          </p>
        </div>
      </div>
      <div className="mt-1 w-64 truncate font-bold text-mainColor3 text-base hover:underline cursor-pointer">{title}</div>
      <div className="w-64 truncate text-mainColor3 text-xs">
        by {author} &nbsp; {createDate}
      </div>
    </div>
  );
}

export default BoardItem;
