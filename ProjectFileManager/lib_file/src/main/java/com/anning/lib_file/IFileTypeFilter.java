package com.anning.lib_file;

import java.util.List;

public interface IFileTypeFilter {

    List<ChooseFileInfo> doFilter(List<ChooseFileInfo> list);
}
