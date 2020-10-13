import { Component, OnInit } from '@angular/core';
import { UserGlobalService } from './services/user-global.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'forum-front';
  constructor(private userService: UserGlobalService) {}

  ngOnInit(): void{
    if (!this.userService.user ){
      this.userService.getUser();
    }
  }
}
