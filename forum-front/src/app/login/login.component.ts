import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user';
import { LoginRESTService } from '../services/login-rest.service';
import { UserGlobalService } from '../services/user-global.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  user = 'user_1';
  password = 'password';
  result: any;

  message: any;

  constructor(private restClient: LoginRESTService, private router: Router, public userService: UserGlobalService) {
  }

  doLogin(): void {
    this.restClient.login(this.user, this.password).subscribe(data => {
        this.router.navigate(['/forum']);
        this.userService.getUser();
        console.log(this.userService.user);
      }, error => {
        console.error(error);
        this.message = 'Error al ingresar';
      });
  }

  doLogout(): void {
    this.restClient.logout().subscribe(data => {
      this.userService.user = null;
    }, error => {
      console.error(error);
    });
  }

  ngOnInit(): void {
  }

}
