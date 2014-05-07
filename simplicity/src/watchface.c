#include "pebble.h"

Window *window;
TextLayer *text_date_layer;
TextLayer *text_time_layer;
//Layer *line_layer;

TextLayer *text_1_layer;
TextLayer *text_3_layer;
TextLayer *text_2_layer;
TextLayer *text_4_layer;
TextLayer *text_5_layer;
TextLayer *text_6_layer;
TextLayer *text_7_layer;
//TextLayer *text_temp_layer;
//TextLayer *text_humid_layer;

char text1[13];
char *text_1;
char text2[13];
char *text_2;
char text3[13];
char *text_3;
char text4[13];
char *text_4;
char text5[13];
char *text_5;
char text6[13];
char *text_6;
char text7[13];
char *text_7;

char scrollText[] = "Bittersweet Symphony!";
int size = 21;
int display = 0;
char displayText[15];

char scrollText2[] = "Bittersweet Symphony!";
int display2 = 0;
char displayText2[15];

/*void line_layer_update_callback(Layer *layer, GContext* ctx) {
  graphics_context_set_fill_color(ctx, GColorWhite);
  graphics_fill_rect(ctx, layer_get_bounds(layer), 0, GCornerNone);
}*/

//int temp = 22;
//int humidity = 37;

void setText(void)
{
  text_layer_set_text(text_1_layer, text_1);
  text_layer_set_text(text_2_layer, text_2);
  text_layer_set_text(text_3_layer, text_3);
  text_layer_set_text(text_4_layer, text_4);
  text_layer_set_text(text_5_layer, text_5);
  text_layer_set_text(text_6_layer, text_6);
  text_layer_set_text(text_7_layer, text_7);
}

void addNullEndings(void)
{
  text1[12] = '\0';
  text2[12] = '\0';
  text3[12] = '\0';
  text4[12] = '\0';
  text5[12] = '\0';
  text6[12] = '\0';
  text7[12] = '\0';
}

static void in_received_handler(DictionaryIterator *received, void *context) {
	Tuple *tuple;
	tuple = dict_find(received, 1);
	if (tuple)
	{
		strncpy(text_1, tuple->value->cstring, 12);
	}
	tuple = dict_find(received, 2);
	if (tuple)
	{
		strncpy(text_2, tuple->value->cstring, 12);
	}
	tuple = dict_find(received, 3);
	if (tuple)
	{
		strncpy(text_3, tuple->value->cstring, 12);
	}
	tuple = dict_find(received, 4);
	if (tuple)
	{
		strncpy(text_4, tuple->value->cstring, 12);
	}
	tuple = dict_find(received, 5);
	if (tuple)
	{
		strncpy(text_5, tuple->value->cstring, 12);
	}
	tuple = dict_find(received, 6);
	if (tuple)
	{
		strncpy(text_6, tuple->value->cstring, 12);
	}
	tuple = dict_find(received, 7);
	if (tuple)
	{
		strncpy(text_7, tuple->value->cstring, 12);
	}
	addNullEndings();
	setText();
	/*
	tuple = dict_find(received, 0);
	if(tuple) {
	temp = (int) tuple->value->uint32;
	}
        tuple = dict_find(received, 2);
        if(tuple) {
        humidity = (int) tuple->value->uint32;
        }
*/
}

void handle_second_tick(struct tm *tick_time, TimeUnits units_changed) {
  /*
  if (sec == 0)
  {
    text_layer_set_text(text_1_layer, "1111");
    sec = 1;
  }
  else
  {
    text_layer_set_text(text_1_layer, "2222");
    sec = 0;
  }
  */
  
  }

void handle_minute_tick(struct tm *tick_time, TimeUnits units_changed) {
  
  int j = 0;
  int total = 14;
  for (int i = display; i < size; i++)
  {
    displayText[j] = scrollText[i];
    j++;
    total--;
    if (total == 0) break;
  }
  for (int i = 0; i < total; i++)
  {
    displayText[j] = scrollText[i];
    j++;
  }
  displayText[14] = '\0';
  text_5 = displayText;
  display++;
  if ((size - display) == 13)
  {
    display = 0;
  }


  int j2 = 0;
  int total2 = 14;
  for (int i = display; i < size; i++)
  {
    displayText2[j2] = scrollText2[i];
    j2++;
    total2--;
    if (total2 == 0) break;
  }
  for (int i = 0; i < total2; i++)
  {
    displayText2[j2] = scrollText2[i];
    j2++;
  }
  displayText2[14] = '\0';
  text_2 = displayText2;
  setText();
  display2++;
  if (display2 == size)
  {
    display2 = 0;
  } 

  // Need to be static because they're used by the system later.
  static char time_text[] = "00:00";
  static char date_text[] = "Xxxxxxxxx 00";
 
  //text_layer_set_text(text_1_layer, "AT 10-20 BT");
  //text_layer_set_text(text_temp_layer, itoa(temp));
  //text_layer_set_text(text_humid_layer, itoa2(humidity));
  char *time_format;


 
  // TODO: Only update the date when it's changed.
  strftime(date_text, sizeof(date_text), "%B %e", tick_time);
  text_layer_set_text(text_date_layer, date_text);


  if (clock_is_24h_style()) {
    time_format = "%R";
  } else {
    time_format = "%I:%M";
  }

  strftime(time_text, sizeof(time_text), time_format, tick_time);

  // Kludge to handle lack of non-padded hour format string
  // for twelve hour clock.
  if (!clock_is_24h_style() && (time_text[0] == '0')) {
    memmove(time_text, &time_text[1], sizeof(time_text) - 1);
  }

  text_layer_set_text(text_time_layer, time_text);
}

void handle_deinit(void) {
  tick_timer_service_unsubscribe();
}

void handle_init(void) {
  window = window_create();
  window_stack_push(window, true /* Animated */);
  window_set_background_color(window, GColorBlack);

  Layer *window_layer = window_get_root_layer(window);

  text_date_layer = text_layer_create(GRect(0, 140, 89, 168));
  text_layer_set_text_color(text_date_layer, GColorWhite);
  text_layer_set_background_color(text_date_layer, GColorClear);
  text_layer_set_font(text_date_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  layer_add_child(window_layer, text_layer_get_layer(text_date_layer));

  text_time_layer = text_layer_create(GRect(90, 140, 143, 167));
  text_layer_set_text_color(text_time_layer, GColorWhite);
  text_layer_set_background_color(text_time_layer, GColorClear);
  text_layer_set_font(text_time_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  layer_add_child(window_layer, text_layer_get_layer(text_time_layer));

  tick_timer_service_subscribe(SECOND_UNIT, handle_minute_tick);
  //tick_timer_service_subscribe(SECOND_UNIT, handle_second_tick);
  // TODO: Update display here to avoid blank display on launch?


  text_1_layer = text_layer_create(GRect(0, 0, 144, 30));
  text_layer_set_text_color(text_1_layer, GColorWhite);
  text_layer_set_background_color(text_1_layer, GColorClear);
  text_layer_set_font(text_1_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  text_layer_set_text(text_1_layer, "AAAAAAAAAAAA");
  layer_add_child(window_layer, text_layer_get_layer(text_1_layer));

  text_2_layer = text_layer_create(GRect(0,20,144,30));
  text_layer_set_text_color(text_2_layer, GColorWhite);
  text_layer_set_background_color(text_2_layer, GColorClear);
  text_layer_set_font(text_2_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  text_layer_set_text(text_2_layer, "bbbbbbbbbbbb");
  layer_add_child(window_layer, text_layer_get_layer(text_2_layer));


  text_3_layer = text_layer_create(GRect(0,40,144,30));
  text_layer_set_text_color(text_3_layer, GColorWhite);
  text_layer_set_background_color(text_3_layer, GColorClear);
  text_layer_set_font(text_3_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  text_layer_set_text(text_3_layer, "CCCCCCCCCCCC");
  layer_add_child(window_layer, text_layer_get_layer(text_3_layer));

  text_4_layer = text_layer_create(GRect(0,60,144,30));
  text_layer_set_text_color(text_4_layer, GColorWhite);
  text_layer_set_background_color(text_4_layer, GColorClear);
  text_layer_set_font(text_4_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  text_layer_set_text(text_4_layer, "DDDDDDDDDDDD");
  layer_add_child(window_layer, text_layer_get_layer(text_4_layer));

  text_5_layer = text_layer_create(GRect(0,80,144,30));
  text_layer_set_text_color(text_5_layer, GColorWhite);
  text_layer_set_background_color(text_5_layer, GColorClear);
  text_layer_set_font(text_5_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  text_layer_set_text(text_5_layer, "EEEEEEEEEEEE");
  layer_add_child(window_layer, text_layer_get_layer(text_5_layer));

  text_6_layer = text_layer_create(GRect(0,100,144,30));
  text_layer_set_text_color(text_6_layer, GColorWhite);
  text_layer_set_background_color(text_6_layer, GColorClear);
  text_layer_set_font(text_6_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  text_layer_set_text(text_6_layer, "FFFFFFFFFFFF");
  layer_add_child(window_layer, text_layer_get_layer(text_6_layer));

  text_7_layer = text_layer_create(GRect(0,120,144,30));
  text_layer_set_text_color(text_7_layer, GColorWhite);
  text_layer_set_background_color(text_7_layer, GColorClear);
  text_layer_set_font(text_7_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
  text_layer_set_text(text_7_layer, "GGGGGGGGGGGG");
  layer_add_child(window_layer, text_layer_get_layer(text_7_layer));

  for (int i = 0; i < 12; i++)
  {
    text1[i] = ' ';
    text2[i] = ' ';
    text3[i] = ' ';
    text4[i] = ' ';
    text5[i] = ' ';
    text6[i] = ' ';
    text7[i] = ' '; 
  }

  addNullEndings();
  text_1 = text1;
  text_2 = text2;
  text_3 = text3;
  text_4 = text4;
  //text_5 = text5;
  text_5 = scrollText;
  text_6 = text6;
  text_7 = text7;
  setText();

/*
    text_temp_layer = text_layer_create(GRect(73,20,70,30));
    text_layer_set_text_color(text_temp_layer, GColorWhite);
    text_layer_set_background_color(text_temp_layer, GColorClear);
    text_layer_set_font(text_temp_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
    text_layer_set_text(text_temp_layer, "...");
    layer_add_child(window_layer, text_layer_get_layer(text_temp_layer));
    
    text_humid_layer = text_layer_create(GRect(73,40,70,30));
    text_layer_set_text_color(text_humid_layer, GColorWhite);
    text_layer_set_background_color(text_humid_layer, GColorClear);
    text_layer_set_font(text_humid_layer, fonts_load_custom_font(resource_get_handle(RESOURCE_ID_FONT_ROBOTO_CONDENSED_21)));
    text_layer_set_text(text_humid_layer, "...");
    layer_add_child(window_layer, text_layer_get_layer(text_humid_layer));
  */  
  app_message_register_inbox_received(in_received_handler); 
  app_message_open(app_message_inbox_size_maximum(), app_message_outbox_size_maximum());

}


int main(void) {
  handle_init();

  app_event_loop();
  
  handle_deinit();
}
