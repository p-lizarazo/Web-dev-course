import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ForumListComponent } from './forum/forum-list/forum-list.component';
import { TopicListComponent } from './topic/topic-list/topic-list.component';
import { TopicViewComponent } from './topic/topic-view/topic-view.component';
import { TopicEditComponent } from './topic/topic-edit/topic-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    ForumListComponent,
    TopicListComponent,
    TopicViewComponent,
    TopicEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
