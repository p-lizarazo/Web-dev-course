import { Topic } from './topic';

export class Comment {
  constructor(
    public id: number,
    public contenido: string,
    public fechaPublicacion: Date,
    public aprobado: boolean,
    public ownerUsername: string,
    public tema: Topic,
    public respuestas: Comment[],
    public padre: Comment,
    public likes: number,
    public dislikes: number,
    public ranking: number,
    public myLike: boolean
  ){}
}
