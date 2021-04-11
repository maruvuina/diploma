export class CommentModel {
    idComment: number;
    idUser: number;
    username: string;
	createdDate: string;
    content: string;
    idParent: number;
    children: Array<CommentModel>;
}