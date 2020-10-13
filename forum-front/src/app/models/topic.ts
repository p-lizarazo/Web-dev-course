import { Forum } from './forum';

export class Topic {
  constructor(
    public id: number,
    public fechaPublicacion: Date,
    public titulo: string,
    public contenido: string,
    public ownerUsername: string,
    public aprobado: boolean,
    public foro: Forum,
    public likes: number,
    public dislikes: number,
    public ranking: number
  ){}
}
