//
//  Header.h
//  UserData
//
//  Created by Eric Chaney on 1/15/16.
//  Copyright © 2016 Eric Chaney. All rights reserved.
//

#ifndef Post_h
#define Post_h
#import <UIKit/UIKit.h>
#import <Parse/Parse.h>

#endif /* Post_h */
@interface Post :UIViewController<UITableViewDataSource,
UITableViewDelegate>
{

NSMutableArray *myData;
    NSArray *usersPosts;
    PFQuery *query;
}

-(IBAction)submitPost:(UIButton *)sender;
-(IBAction)logOut:(UIButton *)sender;

@property (strong, nonatomic) IBOutlet UITextField *postFieldBody;
@property (strong, nonatomic) IBOutlet UITextField *postFieldTitle;
@property (strong, nonatomic) IBOutlet UITableView *myTable;



@end