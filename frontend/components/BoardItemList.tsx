import BoardItem from "./BoardItem";

interface Props {
  title?: string;
  boardData?: any[];
}

function BoardItemList({ title, boardData }: Props) {
  return (
    <div className="mt-6 mb-8">
        <div className="w-full mb-2 flex px-16 text-start font-bold text-mainColor3 text-base">
          {title}
        </div>
      <div className="flex w-full justify-between items-center px-16">
        {boardData?.map((board, index) => (
          <BoardItem
            redImage={board["redImage"]}
            blueImage={board["blueImage"]}
            redTitle={board["redTitle"]}
            blueTitle={board["blueTitle"]}
            title={board["title"]}
            author={board["author"]}
            createDate={board["createDate"]}
          ></BoardItem>
        ))}
      </div>
    </div>
  );
}

export default BoardItemList;
