export class User {
  constructor(
    public name: string,
    public roles: string[]
  ){}

  containsRole(rol: string): boolean
  {
    if (!this.roles){
      return false;
    }

    return this.roles.includes(rol);
  }
}
